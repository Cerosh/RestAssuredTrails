
package com.spotify.outh2.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
public class Followers {

    @JsonProperty("href")
    private Object href;
    @JsonProperty("total")
    private Integer total;

}
