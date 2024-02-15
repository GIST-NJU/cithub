const prompt_P = (semanticsType, semantics) => `
  Let's identify parameters.
  Your task is to perform the following steps to identify parameters:
  Step-1: Identify all the parameters from the following ${semanticsType} delimited by \`\`\`.
  (Note: parameters are the factors like user inputs/attributes/conditions/choices/button and etc in the ${semanticsType} that may influence its behavior.)
  Step-2: For each parameter, give the reasons for identifying it as a parameter.

  ${semanticsType}:

  \`\`\`
  ${semantics}
  \`\`\`
`;

const prompt_P_ConvertToJson = (semanticsType, semantics) => `
Convert your response to the following Json array format:
[
    {{
        "param": "the first parameter you identified",
        "reasons": "the reasons for identifying this parameter as a parameter."
    }},
    {{
        "param": "the second parameter you identified",
        "reasons": "the reasons for identifying this parameter as a parameter."
    }},
    ...
    {{
        "param": "the Nth parameter you identified",
        "reasons": "the reasons for identifying this parameter as a parameter."
    }}
]

`
const prompt_P_ValueType = (semanticsType, semantics, param, reasons) => `
Let's identify the value type of parameters.
Your task is to perform the following steps to identify the value type of \`${param}\` which is a parameter identified from the following ${semanticsType} delimited by\`\`\`.
Step-1:Based on the reasons for identifying \`${param}\` as a parameter of ${semanticsType}, choose one of \`Enumerated\`, \`Boolean(Binary)\`, \`UserInput(Numeric)\`, \`UserInput(Text)\` as the value type of the parameter \`${param}\`.
Step-2:Give the reasons for choosing it as the value type of the parameter \`${param}\`.

Reasons for identifying \`${param}\` as a parameter:${reasons}

${semanticsType}:
\`\`\`
${semantics}
\`\`\`
`
const prompt_P_ValueType_ConvertToJson = (param, reasons) => `
Based on your answer, Complete the following json object:

  {{
    "param":"${param}",
    "param_reasons":"${reasons}",
    "valuetype":"the value type(\`Enumerated\`/\`Boolean(Binary)\`/\`UserInput(Numeric)\`/\`UserInput(Text)\`) you choose for the parameter \`${param}\`",
    "valuetype_reasons":"the reasons you give for choosing this value type as the value type of the parameter \`${param}\`"
  }}

`

const prompt_V_Enumerated_Specified = (semanticsType, semantics, param, param_reasons, valuetype_reasons) => `
The parameter \`${param}\` is identified from the following ${semanticsType} delimited by\`\`\` and the value type of \`${param} \` is Enumerated.
Your task is based on all the information known so far to determine whether all the values of \`${param} \` are explicitly specified in the following ${semanticsType}.

the param_reasons of \`${param} \` : ${param_reasons}
the valuetype_reasons of \`${param} \` : ${valuetype_reasons}

${semanticsType}:
\`\`\`
${semantics}

\`\`\`
`

const prompt_V_ConvertToJson_Enumerated = (semanticsType) => `
  Answer yes if all the values are explicitly specified in the previous \`${semanticsType}\`.
  Answer No if there are values are not explicitly specified in the previous \`${semanticsType}\`.

  Convert your answer to the following JSON format:

    {{
      "answer":"simply answer Yes/No",
      "reasons":"the reasons for giving this answer"
    }}

  `

const prompt_V_Enumerated_ListAllSpecifyValues = (semanticsType, param) => ` 

Now you know the parameter \`${param}\` is identified from the previous ${semanticsType} and the value type of \`${param} \` is Enumerated, and all the values of \`${param} \` are explicitly specified in the previous\`${semanticsType} \`.
Your task is based on all the information known so far to list all the specified values of\`${param}\`.
`


const prompt_V_ConvertToJson_Enumerated_ListAllSpecifyValues = (semanticsType, param, param_reasons, valuetype, valuetype_reasons) => `

Based on your answer, complete the following Json object

{{
  "param":"${param}",
  "param_reasons":"${param_reasons}",
  "valuetype":"${valuetype}",
  "valuetype_reasons":"${valuetype_reasons}",
  "values":"all the specified values in the previous ${semanticsType} you give for the parameter \`${param}\`",
  "values_reasons":"the reasons you give for giving these specified values",

}}

`

const prompt_V_Enumerated_Boundary = (semanticsType, param) => `  
Now you know the parameter \`${param}\` is identified from the previous ${semanticsType} and the value type of \`${param}\` is Enumerated.
However not all the values of \`${param}\` are explicitly specified in it.
Your task is based on all the information known so far to determine if the value domain of \`${param}\` has any explicit boundaries or not.
'''

prompt_V_ConvertToJson_Enumerated_Boundary = '''
Answer yes if there are any explicit boundaries in the value domain of \`${param}\`.
Answer No if there are not any explicit boundaries in the value domain of \`${param}\`.

Convert your answer to the following JSON format:

{
  {
      "answer": "simply answer Yes/No",
    "reasons": "the reasons for giving this answer"
  }
}
`

const prompt_V_Enumerated_YesBoundary = (semanticsType, param) => `

Now you know the parameter \`${param}\` is identified from ${semanticsType} and the value type of \`${param}\` is Enumerated.
However not all the values of \`${param}\` are explicitly specified, but there are explicit boundaries in the value domain of \`${param}\`.
Your task is perform the following steps:
Step-1:Based on all the information known so far to give all the boundary values, and give the reasons why you determine these values.
Step-2:Based on all the information known so far to give some random values that are slightly below each of these boundary values, and give the reasons why you determine these values.
Step-3:Based on all the information known so far to give some random values that are slightly above each of these boundary values, and give the reasons why you determine these values.
Step-4:Based on all the information known so far to give some random values greater than each of these boundary values, and give the reasons why you determine these values.
Step-5:Based on all the information known so far to give some random values less than each of these boundary values, and give the reasons why you determine these values.
`



const prompt_V_ConvertToJson_Enumerated_YesBoundary = (param, param_reasons, valuetype, valuetype_reasons) => `

Based on your answer, complete the following Json object

{
    {
        "param": "${param}",
        "param_reasons": "${param_reasons}",
        "valuetype": "${valuetype}",
        "valuetype_reasons": "${valuetype_reasons}",
        "boundary_values": "the boundary values you give for the parameter \`${param}\`",
        "boundary_values_reasons": "the reasons you give for giving these boundary values",
        "slightly_below_boundary_values": "the values that are slightly below boundary values you give for the parameter \`${param}\`",
        "slightly_below_boundary_values_reasons": "the reasons you give for giving these values that are slightly below boundary values",
        "slightly_above_boundary_values": "the values that are slightly above boundary values you give for the parameter \`${param}\`",
        "slightly_above_boundary_values_reasons": "the reasons you give for giving these values that are slightly above boundary values",
        "greater_than_boundary_values": "the values that are greater than boundary values you give for the parameter \`${param}\`",
        "greater_than_boundary_values_reasons": "the reasons you give for giving these values that are greater than boundary values",
        "less_than_boundary_values": "the values that are less than boundary values you give for the parameter \`${param}\`",
        "less_than_boundary_values_reasons": "the reasons you give for giving these values that are less than boundary values",

  }
}
`

const prompt_V_Enumerated_NoBoundary = (semanticsType, param) => `

Now you know the parameter \`${param}\` is identified from the previous ${semanticsType} and the value type of \`${param}\` is Enumerated.
However not all the values of \`${param}\` are explicitly specified and there are no explicit boundaries in the value domain of \`${param}\`.
Your task is based on all the information known so far to give some random valid values and some random invalid values for \`${param}\`, and give the reasons why you determine these valid and invalid values.
`



const prompt_V_ConvertToJson_Enumerated_NoBoundary = (param, param_reasons, valuetype, valuetype_reasons) => `

Based on your answer, complete the following Json object

{{
  "param":"${param}",
  "param_reasons":"${param_reasons}",
  "valuetype":"${valuetype}",
  "valuetype_reasons":"${valuetype_reasons}",
  "valid_values":"give some random valid values for the parameter \`${param}\`",
  "valid_values_reasons":"the reasons you give for giving these valid values",
  "invalid_values":"give some random invalid values for the parameter \`${param}\`",
  "invalid_values_reasons":"the reasons you give for giving these invalid values"

}}
`


const prompt_V_UserInput_Boundary = (semanticsType, semantics, param, valuetype) => `

The parameter \`${param}\` is identified from the following ${semanticsType} delimited by \`\`\` and the value type of \`${param}\` is ${valuetype}.
Your task is based on all the information known so far to determine if the value domain of \`${param}\` has any explicit boundaries or not.

${semanticsType}:
\`\`\`
${semantics}

\`\`\`
`

const prompt_V_ConvertToJson_UserInput_Boundary = (param) => `
Answer yes if there are any explicit boundaries in the value domain of \`${param}\`.
Answer No if there are not any explicit boundaries in the value domain of \`${param}\`.

Convert your answer to the following JSON format:

  {{
    "answer":"simply answer Yes/No",
    "reasons":"the reasons for giving this answer"
  }}
`
const prompt_V_UserInput_YesBoundary = (semanticsType, param, valuetype) => `

Now you know the parameter \`${param}\` is identified from the previous ${semanticsType} and the value type of \`${param}\` is ${valuetype}.
And there are explicit boundaries in the value domain of \`${param}\`.
Your task is perform the following steps:
Step-1:Based on all the information known so far to give all the boundary values, and give the reasons why you determine these values.
Step-2:Based on all the information known so far to give some random values that are slightly below each of these boundary values, and give the reasons why you determine these values.
Step-3:Based on all the information known so far to give some random values that are slightly above each of these boundary values, and give the reasons why you determine these values.
Step-4:Based on all the information known so far to give some random values greater than each of these boundary values, and give the reasons why you determine these values.
Step-5:Based on all the information known so far to give some random values less than each of these boundary values, and give the reasons why you determine these values.
`

const prompt_V_ConvertToJson_UserInput_YesBoundary = (param, param_reasons, valuetype, valuetype_reasons) => `
Based on your answer, complete the following Json object

  {{
    "param":"${param}",
    "param_reasons":"${param_reasons}",
    "valuetype":"${valuetype}",
    "valuetype_reasons":"${valuetype_reasons}",
    "boundary_values":"the boundary values you give for the parameter\`${param}\`",
    "boundary_values_reasons":"the reasons you give for giving these boundary values",
    "slightly_below_boundary_values":"the values that are slightly below boundary values you give for the parameter \`${param}\`",
    "slightly_below_boundary_values_reasons":"the reasons you give for giving these values that are slightly below boundary values",
    "slightly_above_boundary_values":"the values that are slightly above boundary values you give for the parameter \`${param}\`",
    "slightly_above_boundary_values_reasons":"the reasons you give for giving these values that are slightly above boundary values",
    "greater_than_boundary_values":"the values that are greater than boundary values you give for the parameter \`${param}\`",
    "greater_than_boundary_values_reasons":"the reasons you give for giving these values that are greater than boundary values",
    "less_than_boundary_values":"the values that are less than boundary values you give for the parameter \`${param}\`",
    "less_than_boundary_values_reasons":"the reasons you give for giving these values that are less than boundary values",

  }}
`
const prompt_V_UserInput_NoBoundary = (semanticsType, param, valuetype) => `
Now you know the parameter \`${param}\` is identified from the previous ${semanticsType} and the value type of \`${param}\` is ${valuetype}.
However there is no explicit boundaries in the value domain of \`${param}\`.
Your task is based on all the information known so far to give some random valid values and some random invalid values for \`${param}\`, and give the reasons why you determine these valid and invalid values.
`

const prompt_V_ConvertToJson_UserInput_NoBoundary = (param, param_reasons, valuetype, valuetype_reasons) => `
Based on your answer, complete the following Json object

  {{
    "param":"${param}",
    "param_reasons":"${param_reasons}",
    "valuetype":"${valuetype}",
    "valuetype_reasons":"${valuetype_reasons}",
    "valid_values":"give some random valid values for the parameter \`${param}\`",
    "valid_values_reasons":"the reasons you give for giving these valid values",
    "invalid_values":"give some random invalid values for the parameter \`${param}\`",
    "invalid_values_reasons":"the reasons you give for giving these invalid values"

  }}
`

const prompt_Cons_1 = (semanticsType, semantics, Final_PandV_List) => `
Read the following ${semanticsType} and identified parameters and values of each parameter from it, both of them are delimited by \`\`\`.
Simply response \`done\` when you finish reading.

${semanticsType}:
\`\`\`
${semantics}
\`\`\`
Identified parameters and values of each parameter:
\`\`\`
${Final_PandV_List}
\`\`\`
`

const prompt_Cons_1_Answer = () => `
Convert your answer to the following JSON format:

  {{
    "answer":"simply answer done/not done"
  }}

`

const prompt_Cons_2 = (semanticsType) => `
Let's identify constraints.
Your task is to perform the following steps to identify constraints:
Step-1:Identify all the constraints (both implicit and explicit) in the above ${semanticsType}.
(Note: constraints are restrictions or limitations on the values that certain parameters can take or on the combinations of values for multiple parameters.)
Step-2:For each constraint, identify which parameter and parameter values are involved in it.
`

const prompt_Cons_3 = () => `Step-3:For each constraint give reasons why you determine it is a constraint.`


const prompt_Cons_4_ConvertToJson =()=> `

Convert your response to the following JSON format:
[
  {{
    "constraint": "the first constraint you identified.",
    "params": [
      {{"param": "the first param involved in the first constraint", "value": "the values take for the first param"}},
      {{"param": "the second param involved in the first constraint", "value": "the values take for the second param"}},
      ......
      {{"param": "the Nth param involved in the first constraint", "value": "the values take for the Nth param"}}
    ]
  }},
  {{
    "constraint": "the second constraint you identified.",
    "params": [
      {{"param": "the first param involved in the second constraint", "value": "the values take for the first param"}},
      {{"param": "the second param involved in the second constraint", "value": "the values take for the second param"}},
      ......
      {{"param": "the Nth param involved in the second constraint", "value": "the values take for the Nth param"}}
    ]
  }},
      ......
  {{
    "constraint": "the Nth constraint you identified.",
    "params": [
      {{"param": "the first param involved in the Nth constraint", "value": "the values take for the first param"}},
      {{"param": "the second param involved in the Nth constraint", "value": "the values take for the second param"}},
      ......
      {{"param": "the Nth param involved in the Nth constraint", "value": "the values take for the Nth param"}}
    ]
  }}
]
`


export { 
prompt_Cons_4_ConvertToJson,
prompt_Cons_3, 
prompt_Cons_2, 
prompt_Cons_1_Answer, 
prompt_Cons_1, 
prompt_V_ConvertToJson_UserInput_NoBoundary, 
prompt_V_UserInput_NoBoundary, 
prompt_V_ConvertToJson_UserInput_YesBoundary, 
prompt_V_UserInput_YesBoundary, 
prompt_V_ConvertToJson_UserInput_Boundary, 
prompt_V_UserInput_Boundary, 
prompt_V_ConvertToJson_Enumerated_NoBoundary,
 prompt_V_Enumerated_NoBoundary, 
 prompt_V_ConvertToJson_Enumerated_YesBoundary, 
 prompt_V_Enumerated_YesBoundary, 
 prompt_V_Enumerated_Boundary, 
 prompt_P, 
 prompt_P_ConvertToJson, 
 prompt_P_ValueType, 
 prompt_P_ValueType_ConvertToJson, 
 prompt_V_Enumerated_Specified, 
 prompt_V_ConvertToJson_Enumerated, 
 prompt_V_Enumerated_ListAllSpecifyValues,
 prompt_V_ConvertToJson_Enumerated_ListAllSpecifyValues
};