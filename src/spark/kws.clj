(ns spark.kws)

(defn env-gem
  [env gem-kw]
  (get @(:env/gems-atom-kw env) gem-kw))

(defn gem-role-kws
  [role-kw]
  [:facet/descriptors :descriptors/roles role-kw])

(defn gem-requests-kws
  [role-kw]
  (conj (gem-role-kws role-kw) :role/requests))

(defn gem-request-kws
  [role-kw request-kw]
  (conj (gem-requests-kws role-kw) request-kw))

(defn schema-selectors-kws
  []
  [:facet/descriptors :parse/selectors])
