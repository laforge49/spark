(ns spark.kws)

(defn env-gem
  [env]
   (get @(:env/gems-atom-kw env) (:param/gem-kw env)))

(defn gem-role-kws
  [env]
  [:facet/descriptors :descriptors/roles (:param/role-kw env)])

(defn gem-requests-kws
  [env]
  (conj (gem-role-kws env) :role/requests))

(defn gem-request-kws
  [env role-kw request-kw]
  (conj (gem-requests-kws env) request-kw))

(defn schema-selectors-kws
  []
  [:facet/descriptors :parse/selectors])
