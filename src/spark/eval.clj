(ns spark.eval
  (:require [spark.kws :as kws]))

(defn gem-eval
  [env]
  (let [gem-kw
        (:param/gem env)
        role-kw
        (:param/role env)
        request-kw
        (:param/request env)
        gem
        @(kws/env-gem-atom env gem-kw)
        role
        (get-in gem (kws/gem-role-kws role-kw))
        request
        (request-kw role)
        function-name
        (:eval/function-name request)
        function-symbol
        (symbol function-name)
        function
        (resolve function-symbol)
        ]
    (function (into env request))
    ))
