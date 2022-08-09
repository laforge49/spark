(ns spark.boot
  (:require [spark.kws :as kws]))

(def gems-atom
  (atom {}))

(def initial-env
  {:env/gems-atom-kw gems-atom})

(defn create-gem
  [env]
  (let [gem-kw (:param/gem-kw env)
        gem-value
        {:facet/kw          gem-kw
         :facet/descriptors {:descriptors/roles {}}}
        gem (atom gem-value)
        gems-atom (:env/gems-atom-kw env)]
    (swap! gems-atom assoc gem-kw gem)
    gem))

(defn create-role
  [env]
  (let [gem (:param/gem env)
        role-kw (:param/role-kw env)
        requests-kws (kws/gem-requests-kws role-kw)]
    (swap! gem assoc-in requests-kws {})))

(defn create-request
  [env]
  (let [gem (:param/gem env)
        role-kw (:param/role-kw env)
        request-kw (:param/request-kw env)
        function-name (:param/function-name env)
        requests-kws (kws/gem-requests-kws role-kw)
        requests (get-in @gem requests-kws)
        requests (into requests {request-kw {:eval/function-name function-name}})]
    (swap! gem assoc-in requests-kws requests)))

(defn create-gems
  [env]
  (let [gem (create-gem (into env {:param/gem-kw :gem/fudge}))
        env (into env {:param/gem gem})]
    (let [env (into env {:param/role-kw :roles/test})]
      (create-role env)
      (let [env (into env {:param/request-kw :debug/ribbit-request})]
        (create-request (into env {:param/function-name "spark.debug/ribbit"}))))
    (let [env (into env {:param/role-kw :roles/parse})]
      (create-role env)
      (let [env (into env {:param/request-kw :parse/select-grammars-request})]
        (create-request (into env {:param/function-name "spark.parse/select-grammar"}))
        #_:parse/selectors
        #_[{:parse/value        :facet/kw
            :eval/function-name "spark.parse/select-equal-value"
            :parse/grammar-kw   :gem/facet-kw}
           {:parse/value        :facet/roles
            :eval/function-name "spark.parse/select-equal-value"
            :parse/grammar-kw   :gem/facit-roles}]
        ))))
