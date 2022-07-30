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

(println (env-gem-atom-kws :fudge))
(println (gem-role-kws :parse))
(let [env {}
      env (assoc-in env (env-gem-atom-kws :fudge) (atom {}))
      _ (println (env-gem-atom env :fudge))
      _ (reset! (env-gem-atom env :fudge) {:name "fudge"})
      ]
  (println @(env-gem-atom env :fudge))
  )
