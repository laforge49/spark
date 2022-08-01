(ns spark.kws)

(defn env-gem-atom
  [env gem-kw]
  (get @(:env/gems-atom-kw env) gem-kw))

(defn gem-role-kws
  [role-kw]
  [:facet/roles role-kw])
