package org.cg.services.core.swagger.example;

import org.cg.services.core.util.CGDateTime;
import org.cg.services.core.util.CGDateTimeSerializer;
import org.joda.time.DateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A GreetingMessage POJO
 * @author WZ
 *
 */
@ApiModel(value="GreetingMessage")
public class GreetingMessage {

    private String message;
    @ApiModelProperty(dataType="long")
	private DateTime time;
	private boolean isFormated = false;

	public GreetingMessage() {
		super();
	}
	
	public GreetingMessage(String message, DateTime time) {
		super();
		this.message = message;
		this.time = time;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
        return message;
    }

	public void setTime(DateTime time) {
		this.time = time;
	}
	
    @JsonSerialize(using = CGDateTimeSerializer.class)
	public CGDateTime getTime() {
		return new CGDateTime(this.time, this.isFormated);
	}
    
    public void setFormated(boolean isFormated) {
		this.isFormated = isFormated;
	}
}
