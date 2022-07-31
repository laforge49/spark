(ns spark.parse
  (:require
    [spark.kws :as kws]
    ))

(defn select-first-matching-grammar
  [env]
  (let [input (:param/input env)
        parse-role (:param/parse-role env)
        grammars (:parse/grammars parse-role)
        ]
    (reduce
      (fn [result grammar]
        (if (some? result)
          result
          (let [function-name (:eval/function-name grammar)
                function-symbol (symbol function-name)
                function (resolve function-symbol)]
            (function (into env {:param/input input
                                 :param/grammar grammar})))))
      nil
      grammars)
    ))

(defn select-equal-value
  [env]
  (let [input (:param/input env)
        grammar (:param/grammar env)
        value (:parse/value grammar)]
    (if (= input value)
      grammar
      nil)
    ))
