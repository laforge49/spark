(ns spark.boot
  (:require [spark.kws :as kws]
            [clj-yaml.core :as yaml]))

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

(defn create-selector
  [env]
  (let [gem (:param/gem env)
        grammar-kw (:param/grammar-kw env)
        function-name (:param/function-name env)
        value (:param/value env)
        selectors-kws (kws/gem-selectors-kws)
        selector {:parse/grammar-kw   grammar-kw
                  :eval/function-name function-name}
        selector (if (some? value)
                   (into selector {:parse/value value})
                   selector)
        selectors (get-in @gem selectors-kws [])]
    (swap! gem assoc-in selectors-kws (conj selectors selector))))

(defn create-gems
  [env]
  (let [gem (create-gem (into env {:param/gem-kw :gem/fudge}))
        env (into env {:param/gem gem})]
    #_ (create-selector (into env {:param/grammar-kw :gem/facet-kw
                                :param/function-name "spark.parse/select-equal-value"
                                :param/value :facet/kw}))
    #_ (create-selector (into env {:param/grammar-kw :gem/facit-roles
                                :param/function-name "spark.parse/select-equal-value"
                                :param/value :facet/roles}))
    #_ (let [env (into env {:param/role-kw :roles/test})]
      (create-role env)
      (let [env (into env {:param/request-kw :debug/ribbit-request})]
        (create-request (into env {:param/function-name "spark.debug/ribbit"}))))
    #_ (let [env (into env {:param/role-kw :roles/parse})]
      (create-role env)
      (let [env (into env {:param/request-kw :parse/select-grammars-request})]
        (create-request (into env {:param/function-name "spark.parse/select-grammar"}))
        ))
    (println @gem)
    (println)
    (println (yaml/generate-string @gem))
    ))
