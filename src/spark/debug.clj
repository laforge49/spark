(ns spark.debug
  (:require
    [spark.eval :as eval]
    [spark.kws :as kws]
    ))

(defn ribbit
  [env]
  (println "Ribbit!"))

(let [fudge-atom (atom {:identity/name
                        "fudge"
                        kws/gem-roles-kw
                        {:debug/test
                         {:debug/ribbit-request
                          {:eval/function-name
                           "spark.debug/ribbit"}}
                         :parse/parse
                         {:parse/select-grammars-request
                          {:eval/function-name
                           "spark.parse/select-first-matching-grammar"
                           :parse/grammars
                           [{:parse/value
                             kws/gem-roles-kw
                             :eval/function-name
                             "spark.parse/select-equal-value"}]}}}})
      gems-atom
      (atom {:fudge
             fudge-atom})
      env
      {kws/env-gems-atom-kw
       gems-atom}
      ]

  (eval/gem-eval (into env {:param/gem
                            :fudge
                            :param/role
                            :debug/test
                            :param/request
                            :debug/ribbit-request}))
  )
