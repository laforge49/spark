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
