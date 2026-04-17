package org.vietnamsea.identity.module.auth.service;

import org.vietnamsea.identity.infra.persistence.session.entity.LoginHistoryEntity;
import org.vietnamsea.identity.module.auth.dto.response.IpLocationResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthAnomalyDetector {

  boolean isSuspicious(LoginHistoryEntity last, IpLocationResponse current);

  double calculateDistance(double fromLat, double toLat, double fromLon, double toLon);

  String getClientIp(HttpServletRequest request);

  IpLocationResponse getLocationFromIP(String ip);

}
