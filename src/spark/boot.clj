(ns spark.boot)

(defn add-gems-atom
  [env]
  (let [fudge {:gem/name  "fudge"
               :gem/roles {:roles/test
                           {:debug/ribbit-request
                            {:eval/function-name
                             "spark.debug/ribbit"}}
                           :roles/parse
                           {:parse/select-grammars-request
                            {:eval/function-name
                             "spark.parse/select-first-matching-grammar"
                             :parse/grammars
                             [{:parse/value
                               :gem/roles
                               :eval/function-name
                               "spark.parse/select-equal-value"}]}}}}
        gems-atom
        (atom {:fudge
               (atom fudge)})
        ]
    (into env {:env/gems-atom-kw gems-atom})))