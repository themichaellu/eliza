(defproject clj_test "0.0.1-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.hypergraphdb/hgdb "1.3"]
                 [org.hypergraphdb/hgbdbje "1.3"]]

  :repositories [["hgdb" "http://hypergraphdb.org/maven"]]

  :main ^:skip-aot eliza.core

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}})
