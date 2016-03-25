package org.cg.services.core.util;

import io.swagger.annotations.ApiOperation;
import io.swagger.config.FilterFactory;
import io.swagger.config.Scanner;
import io.swagger.config.SwaggerConfig;
import io.swagger.core.filter.SpecFilter;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.JaxrsScanner;
import io.swagger.jaxrs.config.ReaderConfigUtils;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.properties.Property;
import io.swagger.util.Yaml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This Resource is for Swagger API Documentation
 * Path with dot (.) is getting truncated so need to override and fix path 
 * @author WZ
 *
 */
@Path("/")
public class ApiListingResource {
    Log LOGGER = LogFactory.getLog(ApiListingResource.class);
    @Context
    ServletContext context;
    
    private PropertyNamingStrategy propertyNamingStragegy = PropertyNamingStrategy.LOWER_CASE;

	public PropertyNamingStrategy getPropertyNamingStragegy() {
		return propertyNamingStragegy;
	}

	public void setPropertyNamingStragegy(
			PropertyNamingStrategy propertyNamingStragegy) {
		this.propertyNamingStragegy = propertyNamingStragegy;
	}

	protected synchronized Swagger scan(Application app, ServletConfig sc, String serviceId) {
        Swagger swagger = null;
        Scanner scanner = ServiceAwareScannerFactory.getScanner(serviceId);
        LOGGER.debug("using scanner " + scanner);

        if (scanner != null) {
            SwaggerSerializers.setPrettyPrint(scanner.getPrettyPrint());
            swagger = (Swagger) context.getAttribute("swagger" + serviceId);

            Set<Class<?>> classes = new HashSet<Class<?>>();
            if (scanner instanceof JaxrsScanner) {
                JaxrsScanner jaxrsScanner = (JaxrsScanner) scanner;
                classes = jaxrsScanner.classesFromContext(app, sc);
            } else {
                classes = scanner.classes();
            }
            if (classes != null) {
                Reader reader = new Reader(swagger, ReaderConfigUtils.getReaderConfig(context));
                swagger = reader.read(classes);
                if (scanner instanceof SwaggerConfig) {
                    swagger = ((SwaggerConfig) scanner).configure(swagger);
                } else {
                    SwaggerConfig configurator = (SwaggerConfig) context.getAttribute("reader");
                    if (configurator != null) {
                        LOGGER.debug("configuring swagger with " + configurator);
                        configurator.configure(swagger);
                    } else {
                        LOGGER.debug("no configurator");
                    }
                }
                context.setAttribute("swagger" + serviceId, swagger);
            }
        }
        return swagger;
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/swagger-json")
	@ApiOperation(value = "The swagger definition in JSON", hidden = true)
	public Response getListingJson(
			@Context Application app,
			@Context ServletConfig sc,
			@Context HttpHeaders headers,
			@Context UriInfo uriInfo) {
		String serviceId = uriInfo.getBaseUri().getPath();
		Swagger swagger = (Swagger) context.getAttribute("swagger" + serviceId);
		if (swagger==null) {
			swagger = scan(app, sc, serviceId);
		}
		if (swagger != null) {
			SwaggerSpecFilter filterImpl = FilterFactory.getFilter();
			if (filterImpl != null) {
				SpecFilter f = new SpecFilter();
				swagger = f.filter(swagger,
						filterImpl,
						getQueryParams(uriInfo.getQueryParameters()),
						getCookies(headers),
						getHeaders(headers));
			}
			swagger = convertPropertyNaming(swagger);
			return Response.ok().entity(swagger).build();
		} else {
			return Response.status(404).build();
		}
	}

    @GET
    @Produces("application/yaml")
    @Path("/swagger-yaml")
    @ApiOperation(value = "The swagger definition in YAML", hidden = true)
    public Response getListingYaml(
            @Context Application app,
            @Context ServletConfig sc,
            @Context HttpHeaders headers,
            @Context UriInfo uriInfo) {
		String serviceId = uriInfo.getBaseUri().getPath();
        Swagger swagger = (Swagger) context.getAttribute("swagger" + serviceId);
        if (swagger==null) {
            swagger = scan(app, sc, serviceId);
        }
        try {
            if (swagger != null) {
                SwaggerSpecFilter filterImpl = FilterFactory.getFilter();
                LOGGER.debug("using filter " + filterImpl);
                if (filterImpl != null) {
                    SpecFilter f = new SpecFilter();
                    swagger = f.filter(swagger,
                            filterImpl,
                            getQueryParams(uriInfo.getQueryParameters()),
                            getCookies(headers),
                            getHeaders(headers));
                }
                swagger = convertPropertyNaming(swagger);
                String yaml = Yaml.mapper().writeValueAsString(swagger);
                String[] parts = yaml.split("\n");
                StringBuilder b = new StringBuilder();
                for (String part : parts) {
                    // int pos = part.indexOf("!<");
                    // int endPos = part.indexOf(">");
                    b.append(part);
                    b.append("\n");
                }
                return Response.ok().entity(b.toString()).type("application/yaml").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(404).build();
    }
    
    protected Swagger convertPropertyNaming (Swagger swagger) {
    	if (swagger.getDefinitions() == null || swagger.getDefinitions().isEmpty()) {
    		return swagger;
    	}
    	 //for now, we only need to support CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES
    	if (propertyNamingStragegy==PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES) {
    		Iterator<Map.Entry<String, Property>> propertiesIter;
    		Map<String, Property> properties;
    		for ( Model model:swagger.getDefinitions().values() ) {
    			propertiesIter = model.getProperties().entrySet().iterator();
    			properties = new LinkedHashMap<String, Property>();
    			while (propertiesIter.hasNext()) {
    				Map.Entry<String, Property> property = propertiesIter.next();
    				properties.put( CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, property.getKey()),
    							    property.getValue() );
    				propertiesIter.remove();
    			}
    			model.setProperties(properties);
    		}
    	}
        return swagger;
    }

    protected Map<String, List<String>> getQueryParams(MultivaluedMap<String, String> params) {
        Map<String, List<String>> output = new HashMap<String, List<String>>();
        if (params != null) {
            for (String key : params.keySet()) {
                List<String> values = params.get(key);
                output.put(key, values);
            }
        }
        return output;
    }

    protected Map<String, String> getCookies(HttpHeaders headers) {
        Map<String, String> output = new HashMap<String, String>();
        if (headers != null) {
            for (String key : headers.getCookies().keySet()) {
                Cookie cookie = headers.getCookies().get(key);
                output.put(key, cookie.getValue());
            }
        }
        return output;
    }


    protected Map<String, List<String>> getHeaders(HttpHeaders headers) {
        Map<String, List<String>> output = new HashMap<String, List<String>>();
        if (headers != null) {
            for (String key : headers.getRequestHeaders().keySet()) {
                List<String> values = headers.getRequestHeaders().get(key);
                output.put(key, values);
            }
        }
        return output;
    }
}