(ns spark.eval
  (:require [spark.kws :as kws]))

(defn function-eval
  [env]
  (let [function-name (:param/function-name env)
        function-symbol (symbol function-name)
        function (resolve function-symbol)
        ]
    (function env)
    ))

(defn gem-eval
  [env]
  (let [gem-kw (:param/gem env)
        role-kw (:param/role env)
        request-kw (:param/request env)
        gem @(kws/env-gem-atom env gem-kw)
        role (get-in gem (kws/gem-role-kws role-kw))
        request (request-kw role)
        env (into env {:param/function-name (:eval/function-name request)})
        env (into env request)
        ]
    (function-eval env)
    ))
