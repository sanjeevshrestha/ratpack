/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.codahale.internal;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Metered;
import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class MeteredMethodInterceptor implements MethodInterceptor {

  @Inject
  MetricRegistry metrics;

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    String meterTag = buildMeterTag(invocation.getMethod().getAnnotation(Metered.class), invocation.getMethod());
    metrics.meter(meterTag).mark();
    return invocation.proceed();
  }

  private String buildMeterTag(Metered annotation, Method method) {
    if (annotation.name().isEmpty()) {
      return MetricRegistry.name(method.getDeclaringClass(), method.getName());
    }

    if (annotation.absolute()) {
      return annotation.name();
    }

    return MetricRegistry.name(method.getDeclaringClass(), annotation.name());
  }

}

