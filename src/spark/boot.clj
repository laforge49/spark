(ns spark.boot)

(def gems-atom
  (atom {}))

(def initial-env
  {:env/gems-atom-kw gems-atom})

(defn add-gems-atom
  [env]
  (let [fudge-value
        {:facet/kw    :gem/fudge
         :facet/roles {:roles/test
                       {:debug/ribbit-request
                        {:eval/function-name "spark.debug/ribbit"}}
                       :roles/parse
                       {:parse/select-grammars-request
                        {:eval/function-name "spark.parse/select-grammar"
                         :parse/grammars     {:parse/value        :facet/roles
                                              :eval/function-name "spark.parse/select-equal-value"}}}
                       }}
        gems-atom (:env/gems-atom-kw env)
        ]
    (reset! gems-atom
            {:gem/fudge (atom fudge-value)})
    env))
