(ns spark.pretty
  (:require [clojure.string :as string]))

(defn asString-
  [env]
  (let [value (:param/value env)
        initial-indent (get env :param/initial-indent 0)
        indent-increment (get env :param/indent-increment 2)]
    (cond
      (vector? value)
      (let [prefix (get env :param/prefix "")
            first-prefix (string/join [prefix "- "])]
        (first
          (reduce
            (fn [[lst prefix] item]
              (let [ivs (asString- (into env {:param/value item}))
                    lst (into lst ivs)
                    ]
                [lst first-prefix]))
            [[] first-prefix]
            value)))
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
