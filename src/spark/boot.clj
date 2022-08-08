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
        requests (:param/requests env)
        role-kws (kws/gem-role-kws role-kw)]
    (swap! gem assoc-in role-kws requests)))

(defn create-request
  [env]
  (let [gem-kw (:param/gem-kw env)
        role-kw (:param/role-kw env)
        request-kw (:param/request-kw env)]))

(defn create-gems
  [env]
  (let [gem (create-gem (into env {:param/gem-kw :gem/fudge}))]
    (create-role (into env {:param/gem      gem
                            :param/role-kw  :roles/test
                            :param/requests {:debug/ribbit-request
                                             {:eval/function-name "spark.debug/ribbit"}}}))
    (create-role (into env {:param/gem      gem
                            :param/role-kw  :roles/parse
                            :param/requests {:parse/select-grammars-request
                                             {:eval/function-name "spark.parse/select-grammar"
                                              :parse/grammars     [{:parse/value        :facet/kw
                                                                    :eval/function-name "spark.parse/select-equal-value"}
                                                                   {:parse/value        :facet/roles
                                                                    :eval/function-name "spark.parse/select-equal-value"}]}}}))))
