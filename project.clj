(defproject rs-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ^:replace ["-Xmx8g" "-Xms1g"]
  :dependencies [[org.clojure/clojure "1.7.0-alpha4"]
                 [incanter "1.5.5"]
                 [net.mikera/core.matrix "0.29.1"]
                 [net.mikera/vectorz-clj "0.26.2"]
                 [nio "1.0.3"]])
