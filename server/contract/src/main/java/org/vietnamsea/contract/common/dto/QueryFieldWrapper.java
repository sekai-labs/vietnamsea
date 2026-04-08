package org.vietnamsea.contract.common.dto;

import org.vietnamsea.contract.constant.QueryOperatorEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryFieldWrapper {
  private Object value;
  private QueryOperatorEnum operator;
}
