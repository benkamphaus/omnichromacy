(defproject omnichromacy "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ^:replace ["-d64" "-server"
                       "-Xms1g"
                       "-XX:+UseG1GC"
                       "-Xmx6g"
                       ;"-XX:+UseConcMarkSweepGC" "-XX:+UseParNewGC" "-XX:+CMSParallelRemarkEnabled" 
                       "-XX:+UseCompressedOops" 
                       "-XX:+ExplicitGCInvokesConcurrent"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [incanter "1.5.5"]
                 [net.mikera/core.matrix "0.32.1"]
                 [net.mikera/vectorz-clj "0.28.0"]
                 [net.mikera/core.matrix.stats "0.5.0"]
                 [nio "1.0.3"]])
