(ns spark.kws)

(defn env-gem
  [env gem-kw]
  (get @(:env/gems-atom-kw env) gem-kw))

(defn gem-role-kws
  [role-kw]
  [:facet/descriptors :descriptors/roles role-kw])

(defn gem-requests-kws
  [role-kw]
  [:facet/descriptors :descriptors/roles role-kw :role/requests])

(defn gem-request-kws
  [role-kw request-kw]
  [:facet/descriptors :descriptors/roles role-kw :role/requests request-kw])

(defn gem-selectors-kws
  [role-kw request-kw]
  [:facet/descriptors :descriptors/roles role-kw :role/requests request-kw :parse/selectors])
