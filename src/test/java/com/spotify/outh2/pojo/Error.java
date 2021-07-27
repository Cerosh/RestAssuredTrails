
package com.spotify.outh2.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@Jacksonized
@Generated("jsonschema2pojo")
public class Error {

    @JsonProperty("status")
    private Integer status;
    @JsonProperty("message")
    private String message;

}
