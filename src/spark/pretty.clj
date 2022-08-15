(ns spark.pretty
  (:require [clojure.string :as string]))

(defn asString-
  [env]
  (let [value (:param/value env)
        initial-indent (get env :param/initial-indent 0)
        indent-increment (get env :param/indent-increment 2)]
    ;(println :value value)
    (cond
      #_(vector? value)
      #_(reduce
          (fn [lst item]
            (println :item item)
            (let [ivs (asString- (into env {:param/value item}))
                  _ (println :ivs ivs)
                  iv0 (string/join ["- " (first ivs)])
                  ivs (assoc ivs 0 iv0)]
              lst ())
            lst)
          []
          value)
      (map? value)
      (let [prefix (get env :param/prefix "")
            unprefix ""]
        (first
          (reduce
            (fn [[lst prefix] [k v]]
              (let [env (into env {:param/prefix prefix})
                    kv (first (asString- (into env {:param/value k})))
                    kpv (string/join [kv ":"])
                    lst (conj lst kpv)
                    lst (into lst (asString- (into env {:param/value          v
                                                        :param/initial-indent (+ initial-indent indent-increment)})))]
                [lst unprefix]))
            [[] prefix]
            value)))
      :else
      (let [initial-indent (get env :param/initial-indent 0)
            prefix
            (if (= initial-indent 0)
              ""
              (string/join (repeat initial-indent " ")))
            s (pr-str value)
            j (string/join [prefix s])]
        [j]))))

(defn asString
  [env]
  (string/join "\n" (into ["---"] (asString- env))))

(defn debug
  [value]
  (println (asString {:param/value value}))
  (flush))
