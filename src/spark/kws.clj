(ns spark.kws)

(def env-gems-atom-kw
  :spark/env-gems-atom)

(defn env-gems-atom
  [env]
  (get env env-gems-atom-kw))

(defn env-gem-atom
  [env gem-kw]
  (get @(env-gems-atom env) gem-kw))

(defn gem-role-kws
  [role-kw]
  [:gem/roles role-kw])
