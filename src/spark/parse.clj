(ns spark.parse
  (:require
    [spark.eval :as eval]
    [spark.kws :as kws]
    ))

(defn select-first-matching-grammar
  [env]
  (let [parse-role (:param/parse-role env)
        grammars (:parse/grammars parse-role)
        ]
    (reduce
      (fn [result grammar]
        (if (some? result)
          result
          (eval/function-eval (into env {:param/function-name (:eval/function-name grammar)
                               :param/input         (:param/input env)
                               :param/grammar       grammar}))))
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
