(ns spark.parse
  (:require
    [spark.kws :as kws]
    ))

(defn select-first-matching-grammar
  [env]
  )

(defn select-equal-value
  [env]
  (let [input (:param/input env)
        grammer (:param/grammer env)
        value (:parse/value grammer)]
    (if (= input value)
      grammer
      nil)
    ))
