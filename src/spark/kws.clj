(ns spark.kws)

(def env-gem-atoms-kw
  :spark/env-gem-atoms)

(defn env-gem-atom-kws
  [gem-kw]
  [env-gem-atoms-kw gem-kw])

(defn env-gem-atom
  [env gem-kw]
  (get-in env (env-gem-atom-kws gem-kw)))

(def gem-roles-kw
  :spark/gem-roles)

(defn gem-role-kws
  [role-kw]
  [gem-roles-kw role-kw])

(let [fudge-atom (atom {:name "fudge"
                        gem-roles-kw {:parse {}}})
      env (-> {}
              (assoc-in (env-gem-atom-kws :fudge) fudge-atom)
              )
      ]
  (println @(env-gem-atom env :fudge))
  (println (get-in @fudge-atom (gem-role-kws :parse)))
  )
