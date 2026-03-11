package org.vietnamsea.identity.common.dto;

import org.vietnamsea.identity.constant.QueryOperatorEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryFieldWrapper {
  private Object value;
  private QueryOperatorEnum operator;
}
