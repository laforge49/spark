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
      (boot/create-gems env)
      (eval/gem-eval (into env {:param/gem-kw  :gem/fudge
                                :param/role-kw :roles/test
                                :param/request :debug/ribbit-request}))
      (eval/gem-eval (into env {:param/gem-kw  :gem/fudge
                                :param/role-kw :roles/test
                                :param/request :debug/print-value}))
      (println :facet/id (parse/select-grammar (into env {:param/gem-kw :gem/fudge
                                                              :param/input  :facet/id})))
      )
    (println "Fin")
    (catch Exception e
      (stacktrace/print-stack-trace e))))
