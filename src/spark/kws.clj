(ns spark.kws)

(def env-gems-atom-kw
  :spark/env-gems-atom)

(defn env-gems-atom
  [env]
  (get env env-gems-atom-kw))

(defn env-gem-atom
  [env gem-kw]
  (get @(env-gems-atom env) gem-kw))

(def gem-roles-kw
  :spark/gem-roles)

(defn gem-role-kws
  [role-kw]
  [gem-roles-kw role-kw])

(defn ribbit
  [env]
  (println "Ribbit!"))

(defn select-first-matching-grammar
  [env]
  )

(defn select-equal-value
  [env])

(let [fudge-atom (atom {:name
                        "fudge"
                        gem-roles-kw
                        {:test
                         {:ribbit-request
                          {:function-name
                           "spark.kws/ribbit"}}
                         :parse
                         {:select-grammars-request
                          {:function-name
                           "select-first-matching-grammar"
                           :grammars
                           [{:value
                             gem-roles-kw
                             :function-name
                             "select-equal-value"}]}}}})
      gems-atom
      (atom {:fudge
             fudge-atom})
      env
      {env-gems-atom-kw
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
          @(env-gem-atom env gem-kw)
          role
          (get-in gem (gem-role-kws role-kw))
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
