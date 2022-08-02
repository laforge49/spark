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
         :facet/descriptors {:descriptors/roles
                             {:roles/test
                              {:debug/ribbit-request
                               {:eval/function-name "spark.debug/ribbit"}}
                              :roles/parse
                              {:parse/select-grammars-request
                               {:eval/function-name "spark.parse/select-grammar"
                                :parse/grammars     [{:parse/value        :facet/kw
                                                      :eval/function-name "spark.parse/select-equal-value"}
                                                     {:parse/value        :facet/roles
                                                      :eval/function-name "spark.parse/select-equal-value"}]}}
                              }}}
        gem (atom gem-value)
        gems-atom (:env/gems-atom-kw env)
        ]
    (swap! gems-atom assoc gem-kw gem)
    env))

(defn create-role
  [env]
  (let [gem-kw (:param/gem-kw env)
        role-kw (:param/role-kw env)
        requests (:param/requests env)
        gem (kws/env-gem env gem-kw)
        role-kws (kws/gem-role-kws role-kw)]
    (swap! gem assoc role-kws requests)
    ))

(defn create-gems
  [env]
  (create-gem (into env {:param/gem-kw :gem/fudge}))
  )
