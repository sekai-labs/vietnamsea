package org.vietnamsea.identity.module.auth.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vietnamsea.identity.infra.persistence.session.entity.LoginHistoryEntity;
import org.vietnamsea.identity.module.auth.dto.response.IpLocationResponse;
import org.vietnamsea.identity.module.auth.service.AuthAnomalyDetector;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthAnomalyDetectorImpl implements AuthAnomalyDetector {

  @Override
  public boolean isSuspicious(LoginHistoryEntity last, IpLocationResponse current) {
    if (last == null) {
      return false;
    }

    var distance = calculateDistance(
        last.getLat(), current.getLat(),
        last.getLon(), current.getLon());

    if (distance > 300) {
      return true;
    }
    if (!last.getCity().equalsIgnoreCase(current.getCity())) {
      return true;
    }
    return false;
  }

  @Override
  public String getClientIp(HttpServletRequest request) {
    String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      String cfHeader = request.getHeader("CF-Connecting-IP");
      if (cfHeader == null) {
        return request.getRemoteAddr();
      }
      return cfHeader;
    }
    return xfHeader.split(",")[0];
  }

  @Override
  public IpLocationResponse getLocationFromIP(String ip) {
    String url = "http://ip-api.com/json/" + ip;
    var rest = new RestTemplate();
    try {
      return rest.getForObject(url, IpLocationResponse.class);
    } catch (Exception ex) {
      return null;
    }
  }

  @Override
  public double calculateDistance(double fromLat, double toLat, double fromLon, double toLon) {
    final int R = 6371;
    double dLat = Math.toRadians(toLat - fromLat);
    double dLon = Math.toRadians(toLon - fromLon);

    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(Math.toRadians(fromLat)) *
            Math.cos(Math.toRadians(toLat)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }
}
