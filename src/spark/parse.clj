(ns spark.parse
  (:require
    [spark.eval :as eval]
    [spark.kws :as kws]))

(defn select-grammar
  [env]
  (let [gem (:param-gem env)
        selectors (get-in @gem (kws/gem-selectors-kws))]
    (reduce
      (fn [result selector]
        (if (some? result)
          result
          (eval/function-eval (into env {:param/function-name (:eval/function-name selector)
                                         :param/selector       selector}))))
      nil
      selectors)
    ))

(defn select-equal-value
  [env]
  (let [input (:param/input env)
        selector (:param/selector)
        grammar-kw (:parse/grammar-kw selector)
        value (:parse/value selector)]
    (if (= input value)
      grammar-kw
      nil)
    ))
