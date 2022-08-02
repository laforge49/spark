(ns spark.core
  (:require
    [clojure.stacktrace :as stacktrace]
    [spark.boot :as boot]
    [spark.debug :as debug]
    [spark.eval :as eval]
    [spark.kws :as kws]
    [spark.parse :as parse]
    )
  (:gen-class))

(defn -main
  [& args]
  (println)
  (try
    (let [env boot/initial-env]
      (boot/create-gem (into env {:param/gem-kw :gem/fudge}))
      (eval/gem-eval (into env {:param/gem-kw     :gem/fudge
                                :param/role    :roles/test
                                :param/request :debug/ribbit-request}))
      )
    (println "Fin")
    (catch Exception e
      (stacktrace/print-stack-trace e))))
