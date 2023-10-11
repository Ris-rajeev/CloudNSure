package com.realnet.icon.response;

import java.util.List;

import com.realnet.fnd.response.PageResponse;
import com.realnet.icon.entity.PinnedEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PinnedResponse  extends PageResponse{
	
	@ApiModelProperty(required = true, value = "")
	 private List<PinnedEntity> favourite;

}
