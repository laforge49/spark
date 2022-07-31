(ns spark.eval
  (:require [spark.kws :as kws]))

(let [fudge-atom (atom {:name
                        "fudge"
                        kws/gem-roles-kw
                        {:test
                         {:ribbit-request
                          {:function-name
                           "spark.debug/ribbit"}}
                         :parse
                         {:select-grammars-request
                          {:function-name
                           "select-first-matching-grammar"
                           :grammars
                           [{:value
                             kws/gem-roles-kw
                             :function-name
                             "select-equal-value"}]}}}})
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
          (:function-name request)
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
                       :test
                       :request
                       :ribbit-request}))
  )
