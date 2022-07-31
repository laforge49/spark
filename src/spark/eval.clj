(ns spark.eval
  (:require [spark.kws :as kws]))

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
                           "spark.eval/select-first-matching-grammar"
                           :parse/grammars
                           [{:parse/value
                             kws/gem-roles-kw
                             :eval/function-name
                             "spark.eval/select-equal-value"}]}}}})
      gems-atom
      (atom {:fudge
             fudge-atom})
      env
      {kws/env-gems-atom-kw
       gems-atom}
      ]

  (defn gem-eval
    [env]
    (let [gem-kw
          (:gem env)
          role-kw
          (:role env)
          request-kw
          (:request env)
          gem
          @(kws/env-gem-atom env gem-kw)
          role
          (get-in gem (kws/gem-role-kws role-kw))
          request
          (request-kw role)
          function-name
          (:eval/function-name request)
          function-symbol
          (symbol function-name)
          function
          (resolve function-symbol)
          ]
      (function (into env request))
      ))

  (gem-eval (into env {:gem
                       :fudge
                       :role
                       :debug/test
                       :request
                       :debug/ribbit-request}))
  )
