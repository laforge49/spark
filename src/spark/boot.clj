(ns spark.boot
  (:require [spark.kws :as kws]
            [spark.pretty :as pretty]))

(def gems-atom
  (atom {}))

(def initial-env
  {:env/gems-atom-kw gems-atom})

(defn create-gem
  [env]
  (let [gem-kw (:param/gem-kw env)
        gem-value
        {:facet/id          gem-kw
         :facet/descriptors {:descriptors/roles {}}}
        gem (atom gem-value)
        gems-atom (:env/gems-atom-kw env)]
    (swap! gems-atom assoc gem-kw gem)
    gem))

(defn create-role
  [env]
  (let [gem-kw (:param/gem-kw env)
        gem (kws/env-gem env gem-kw)
        role-kw (:param/role-kw env)
        requests-kws (kws/gem-requests-kws role-kw)]
    (swap! gem assoc-in requests-kws {})))

(defn create-request
  [env]
  (let [gem-kw (:param/gem-kw env)
        gem (kws/env-gem env gem-kw)
        role-kw (:param/role-kw env)
        request-kw (:param/request-kw env)
        function-name (:param/function-name env)
        request-params (get env :param/request-params {})
        request (into {:eval/function-name function-name} request-params)
        requests-kws (kws/gem-requests-kws role-kw)
        requests (get-in @gem requests-kws)
        requests (into requests {request-kw request})]
    (swap! gem assoc-in requests-kws requests)))

(defn create-selector
  [env]
  (let [gem-kw (:param/gem-kw env)
        gem (kws/env-gem env gem-kw)
        schema-kw (:param/schema-kw env)
        function-name (:param/function-name env)
        value (:param/value env)
        selectors-kws (kws/gem-selectors-kws)
        selector {:parse/schema-kw    schema-kw
                  :eval/function-name function-name}
        selector (if (some? value)
                   (into selector {:parse/value value})
                   selector)
        selectors (get-in @gem selectors-kws [])]
    (swap! gem assoc-in selectors-kws (conj selectors selector))))

(defn create-gems
  [env]
  (let [env (into env {:param/gem-kw :gem/gem-schema})
        gem (create-gem env)]
    (create-selector (into env {:param/schema-kw     :gem/facet-id-schema
                                :param/function-name "spark.parse/select-equal-key"
                                :param/value         :facet/id}))
    (create-selector (into env {:param/schema-kw     :gem/facet-descriptors-schema
                                :param/function-name "spark.parse/select-equal-key"
                                :param/value         :facet/descriptors}))
    (let [env (into env {:param/role-kw :roles/test})]
      (create-role env)
      (create-request (into env {:param/request-kw    :debug/ribbit-request
                                 :param/function-name "spark.debug/ribbit"}))
      (create-request (into env {:param/request-kw     :debug/print-value
                                 :param/function-name  "spark.debug/print-value"
                                 :param/request-params {:param/value "Sam I am"}})))
    (println @gem)
    (pretty/debug @gem)
    ))
