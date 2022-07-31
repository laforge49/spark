(ns spark.debug
  (:require
    [spark.boot :as boot]
    [spark.eval :as eval]
    [spark.kws :as kws]
    ))

(defn ribbit
  [env]
  (println "Ribbit!"))

(eval/gem-eval (into (boot/add-gems-atom {}) {:param/gem :fudge
                               :param/role :roles/test
                               :param/request :debug/ribbit-request}))
