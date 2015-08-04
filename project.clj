(defproject clj_test "0.0.1-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.hypergraphdb/hgdb "1.3"]
                 [org.hypergraphdb/hgbdbje "1.3"]
                 [de.dfki.mary/voice-cmu-slt-hsmm "5.1.2"]]

  :repositories [["hgdb" "http://hypergraphdb.org/maven"]
                 ["jcenter" "http://jcenter.bintray.com"]
                 ["ojo" "http://oss.jfrog.org/artifactory/libs-release"]]

  :main ^:skip-aot eliza.core_interpreter

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}})
