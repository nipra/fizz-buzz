(defproject fizz-buzz "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "1.1.3"]
                 [ring/ring-json "0.1.2"]
                 [hiccup "1.0.0"]]
  :plugins [[lein-ring "0.7.1"]]
  :ring {:handler fizz-buzz.core/handler
         :auto-reload? false}
  :dev-dependencies [[swank-clojure "1.4.0"]])
