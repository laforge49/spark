(ns spark.kws)

(defn env-gem
  [env gem-kw]
  (get @(:env/gems-atom-kw env) gem-kw))

(defn gem-role-kws
  [role-kw]
  [:facet/descriptors :descriptors/roles role-kw])
