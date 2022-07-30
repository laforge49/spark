(ns spark.kws)

(def env-gems-atom-kw
  :spark/env-gems-atom)

(defn env-gems-atom
  [env]
  (get env env-gems-atom-kw))

(defn env-gem-atom
  [env gem-kw]
  (get @(env-gems-atom env) gem-kw))

(def gem-roles-kw
  :spark/gem-roles)

(defn gem-role-kws
  [role-kw]
  [gem-roles-kw role-kw])

(defn select-first-matching-grammar
  [env]
  )

(defn select-equal-value
  [env])

(let [fudge-atom (atom {:name
                        "fudge"
                        gem-roles-kw
                        {:parse
                         {:select-grammars-request
                          {:function
                           select-first-matching-grammar
                           :grammars
                           [{:value
                             gem-roles-kw
                             :function
                             select-equal-value}]}}}})
      gems-atom
      (atom {:fudge
             fudge-atom})
      env
      {env-gems-atom-kw
       gems-atom}
      ]
  (println @(env-gem-atom env :fudge))
  (println (get-in @fudge-atom (gem-role-kws :parse)))
  )
