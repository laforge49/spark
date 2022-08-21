(ns spark.parse
  (:require
    [spark.eval :as eval]
    [spark.kws :as kws]))

(defn select-schema-kw
  [env]
  (let [gem-kw (:param/gem-kw env)
        gem (kws/env-gem env gem-kw)
        selectors (get-in @gem (kws/gem-selectors-kws))]
    (reduce
      (fn [result selector]
        (if (some? result)
          result
          (eval/function-eval (into env {:param/function-name (:eval/function-name selector)
                                         :param/selector      selector}))))
      nil
      selectors)))

(defn parse-collection
  [env]
  (let [collection-input (:param/input env)]
    (reduce
      (fn [post-map item]
        (let [schema-kw (select-schema-kw (into env {:param/input item}))
              post-list (get post-map schema-kw [])
              post-list (conj post-list item)]
          (assoc post-map schema-kw post-list)))
      {}
      collection-input)))

(defn select-equal-key
  [env]
  (let [entry-input (:param/input env)
        key-input (key entry-input)
        selector (:param/selector env)
        schema-kw (:parse/schema-kw selector)
        value (:parse/value selector)]
    (if (= key-input value)
      schema-kw
      nil)))
