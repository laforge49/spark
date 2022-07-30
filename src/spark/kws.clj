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

(let [env {env-gem-atoms-kw {:fudge {:name "fudge"}}}]
  (println (env-gem-atom-kws :fudge))
  (println (env-gem-atom env :fudge))
  (println (gem-role-kws :parse))
  )
