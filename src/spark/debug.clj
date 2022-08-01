(ns spark.debug
  (:require
    [spark.boot :as boot]
    [spark.eval :as eval]
    [spark.kws :as kws]
    ))

(defn ribbit
  [env]
  (println "Ribbit!"))

(let [env (boot/add-gems-atom {})]
  (eval/gem-eval (into env {:param/gem     :gem/fudge
                            :param/role    :roles/test
                            :param/request :debug/ribbit-request}))
  )
