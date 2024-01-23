(ns exfn.logic 
  (:require [re-frame.core :as rf]))

(def colors
  {:red    "red"
   :orange "orange"
   :yellow "yellow"
   :green  "green" 
   :pink   "pink"
   :brown  "brown"
   :cyan   "cyan"
   :blue   "blue"})

(defn generate-solution [no-colors]
  (keys (take no-colors (shuffle colors))))

 (defn check-guess [solution guess]
   (map (fn [s g]
          (cond
            (= s g)            :pos-and-color
            ((set solution) g) :color
            :else              :wrong))
        solution guess))

(check-guess 
 [:red :orange :green :pink]
 [:pink :orange :green :blue])

(comment
  
  
  
  )
  
  