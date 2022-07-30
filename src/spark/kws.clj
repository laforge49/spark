(ns spark.kws)

(def env-gems-kw
  :spark/env-gems)

(defn env-gem-kws
  [gem-kw]
  [env-gems-kw gem-kw])

(def gem-roles-kw
  :spark/gem-roles)

(defn gem-roles-kws
  [role-kw]
  [gem-roles-kw role-kw])

(println (env-gem-kws :fudge))
(println (gem-roles-kws :parse))
