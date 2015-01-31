/*
 * Copyright 2015 the original author or authors.
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

package ratpack.server.internal;

import ratpack.func.Function;
import ratpack.func.NoArgAction;
import ratpack.registry.Registries;
import ratpack.registry.Registry;
import ratpack.server.RatpackServer;

import java.util.Optional;

public abstract class ServerCapturer {

  private static final ThreadLocal<Overrides> OVERRIDES_HOLDER = ThreadLocal.withInitial(Overrides::new);
  private static final ThreadLocal<RatpackServer> SERVER_HOLDER = new ThreadLocal<>();

  private ServerCapturer() {
  }

  public static class Overrides {
    private int port = -1;
    private Function<? super Registry, ? extends Registry> registryFunction = Function.constant(Registries.empty());
    private Boolean development;

    public Overrides port(int port) {
      this.port = port;
      return this;
    }

    public Overrides registry(Function<? super Registry, ? extends Registry> registryFunction) {
      this.registryFunction = registryFunction;
      return this;
    }

    public Overrides development(boolean development) {
      this.development = development;
      return this;
    }

    public int getPort() {
      return port;
    }

    public Function<? super Registry, ? extends Registry> getRegistryFunction() {
      return registryFunction;
    }

    public Boolean isDevelopment() {
      return development;
    }
  }

  public static Optional<RatpackServer> capture(NoArgAction bootstrap) throws Exception {
    return capture(new Overrides(), bootstrap);
  }

  public static Optional<RatpackServer> capture(Overrides overrides, NoArgAction bootstrap) throws Exception {
    OVERRIDES_HOLDER.set(overrides);
    try {
      bootstrap.execute();
    } finally {
      OVERRIDES_HOLDER.remove();
    }

    RatpackServer ratpackServer = SERVER_HOLDER.get();
    SERVER_HOLDER.remove();
    return Optional.ofNullable(ratpackServer);
  }

  public static Overrides capture(RatpackServer server) throws Exception {
    SERVER_HOLDER.set(server);
    return OVERRIDES_HOLDER.get();
  }

}
