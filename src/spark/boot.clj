(ns spark.boot)

(def gems-atom
  (atom {}))

(def initial-env
  {:env/gems-atom-kw gems-atom})

(defn create-gem
  [env]
  (let [gem-kw (:param/gem-kw env)
        fudge-value
        {:facet/kw    gem-kw
         :facet/roles {:roles/test
                       {:debug/ribbit-request
                        {:eval/function-name "spark.debug/ribbit"}}
                       :roles/parse
                       {:parse/select-grammars-request
                        {:eval/function-name "spark.parse/select-grammar"
                         :parse/grammars     [{:parse/value        :facet/kw
                                               :eval/function-name "spark.parse/select-equal-value"}
                                              {:parse/value        :facet/roles
                                               :eval/function-name "spark.parse/select-equal-value"}]}}
                       }}
        fudge (atom fudge-value)
        gems-atom (:env/gems-atom-kw env)
        ]
    (swap! gems-atom assoc gem-kw fudge)
    env))
